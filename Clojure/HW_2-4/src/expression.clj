(require '[clojure.string :as string])

; constants and vars
(defn constant [val] (fn [_] val))
(defn variable [name] (fn [vars] (get vars name)))
(def x (variable "x"))
(def y (variable "y"))
(def z (variable "z"))

; math
(defn div
  ([x] (/ 1.0 x))
  ([x y] (/ (double x) (double y)))
  ([x y & args] (/ (div x y) (reduce div args))))
(def math-ln #(Math/log (Math/abs %1)))
(def math-avg #(let [c (count %&)] (div (apply + %&) c)))
(def math-med #(let [k (div (count %&) 2)] (nth (sort %&) k)))
(def math-pow #(Math/pow %1 %2))
(def math-log #(div (math-ln %2) (math-ln %1)))
(defn math-bitwise [f] #(Double/longBitsToDouble (f (Double/doubleToLongBits %1) (Double/doubleToLongBits %2))))
(def math-xor (math-bitwise bit-xor))
(def math-and (math-bitwise bit-and))
(def math-or (math-bitwise bit-or))

; operations
(defn basic-op [f] #(fn [vars] (apply f ((apply juxt %&) vars))))
(def add (basic-op +))
(def subtract (basic-op -))
(def multiply (basic-op *))
(def divide (basic-op div))
(def negate (basic-op #(- %1)))
(def min (basic-op clojure.core/min))
(def max (basic-op clojure.core/max))
(def exp (basic-op #(Math/exp %1)))
(def ln (basic-op math-ln))
(def avg (basic-op math-avg))
(def med (basic-op math-med))
(def pw (basic-op math-pow))
(def lg (basic-op math-log))

; parse
(def lexemes {
              "+"      add
              "-"      subtract
              "*"      multiply
              "/"      divide
              "negate" negate
              "min"    min
              "max"    max
              "exp"    exp
              "ln"     ln
              "avg"    avg
              "med"    med
              "pw"     pw
              "lg"     lg
              })
(def vars-function {
                    "x" x
                    "y" y
                    "z" z
                    })

(defn parse [lexs vars const]
  (letfn [(extract [arg]
            (cond
              (list? arg) (let [[f & args] arg] (apply (lexs (str f)) (map extract args)))
              (symbol? arg) (vars (name arg))
              :else (const arg)))]
    (fn [expression] (extract (read-string expression)))))
(def parseFunction (parse lexemes vars-function constant))

; ----------------------  Objects ----------------------

(defn proto-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :proto) (proto-get (obj :proto) key)))
(defn proto-call [obj key & args] (apply (proto-get obj key) obj args))
(defn field [key] #(proto-get % key))
(defn method [key] #(apply proto-call %1 key %&))
(defn constructor [obj proto] #(apply obj {:proto proto} %&))

(def _name (field :name))
(def _value (field :value))
(def _args (field :args))
(def _op (field :op))
(def diff-rule (field :diff-rule))
(def evaluate (method :evaluate))
(def toString (method :toString))
(def toStringSuffix (method :toStringSuffix))
(def toStringInfix (method :toStringInfix))
(def join-args (method :join-args))
(def diff (method :diff))

(def Constant)
(def const-proto
  {
   :evaluate       (fn [this _] (_value this))
   :toString       #(let [v (_value %)] (if (double? v) (format "%.1f" v) (str v)))
   :toStringSuffix #(toString %)
   :toStringInfix  #(toString %)
   :diff           (fn [_ _] (Constant 0))
   })
(defn const-constructor [this val] (assoc this :value val))
(def Constant (constructor const-constructor const-proto))
(def ZERO (Constant 0))
(def ONE (Constant 1))

(def variable-proto
  {
   :evaluate       #(get %2 (_name %1))
   :toString       #(_name %)
   :toStringSuffix #(toString %)
   :toStringInfix  #(toString %)
   :diff           #(if (= (_name %1) %2) ONE ZERO)
   })
(defn variable-constructor [this name] (assoc this :name name))
(def Variable (constructor variable-constructor variable-proto))
(def X (Variable "x"))
(def Y (Variable "y"))
(def Z (Variable "z"))
(defn par [inner] (str "(" inner ")"))

(def basic-proto
  {
   :diff           #((diff-rule %1) (_args %1) %2)
   :evaluate       (fn [this vars] (apply (_op this) (mapv #(evaluate % vars) (_args this))))
   :join-args      #(string/join " " (mapv %2 (_args %1)))
   :toString       #(par (str (_name %) " " (join-args % toString)))
   :toStringSuffix #(par (str (join-args % toStringSuffix) " " (_name %)))
   :toStringInfix  #(if (= (count (_args %)) 1)
                      (str (_name %) "(" (toStringInfix (first (_args %))) ")")
                      (par (string/join (str " " (_name %) " ") (mapv toStringInfix (_args %)))))
   })
(defn basic [operation operand diff]
  (letfn [(f [this & args] (assoc this :args args))]
    (let [f-proto {:proto basic-proto :op operation :name operand :diff-rule diff}]
      (constructor f f-proto))))

(defn to-diff [args var] (mapv #(diff % var) args))
(def liner-diff #(apply %1 (to-diff %2 %3)))

(def Add (basic + "+" #(liner-diff Add %1 %2)))
(def Sum (basic + "sum" #(liner-diff Sum %1 %2)))
(def Avg (basic math-avg "avg" #(liner-diff Avg %1 %2)))
(def Subtract (basic - "-" #(liner-diff Subtract %1 %2)))
(def Negate (basic - "negate" #(liner-diff Negate %1 %2)))

(defn take-rest [rest constructor] (if (= (count rest) 1) (first rest) (apply constructor rest)))
(defn recur-dif [[x & rest] rule var constructor]
  (let [y (take-rest rest constructor)] (rule x y (diff x var) (diff y var))))
(def Multiply (basic * "*"
                     (fn [args var]
                       (letfn [(diff-multiply [x y dx dy]
                                 (Add
                                   (Multiply dx y)
                                   (Multiply dy x)))]
                         (recur-dif args diff-multiply var Multiply)))))
(def Divide (basic div "/"
                   (fn [args var]
                     (letfn [(diff-divide [x y dx dy]
                               (Divide
                                 (Subtract
                                   (Multiply dx y)
                                   (Multiply dy x)
                                   )
                                 (Multiply y y)))]
                       (recur-dif args diff-divide var Divide)))))
(def Xor (basic math-xor "^" #(liner-diff Xor %1 %2)))
(def And (basic math-and "&" #(liner-diff And %1 %2)))
(def Or (basic math-or "|" #(liner-diff Or %1 %2)))
(def object-lexemes
  {
   "+"      Add
   "-"      Subtract
   "*"      Multiply
   "/"      Divide
   "negate" Negate
   "avg"    Avg
   "sum"    Sum
   "^"      Xor
   "&"      And
   "|"      Or
   })
(def vars-object {
                  "x" X
                  "y" Y
                  "z" Z
                  })
(def parseObject (parse object-lexemes vars-object Constant))

; ---------------------- combinators ----------------------

; ---------- lib ----------
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)

(defn _show [result]
  (if (-valid? result) (str "-> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result))))
                       "!"))
(defn tabulate [parser inputs]
  (run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (_show (parser input)))) inputs))
(defn _empty [value] (partial -return value))
(defn _char [p]
  (fn [[c & cs]]
    (if (and c (p c)) (-return c cs))))
(defn _map [f result]
  (if (-valid? result)
    (-return (f (-value result)) (-tail result))))
(defn _combine [f a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar)
        (_map (partial f (-value ar))
              ((force b) (-tail ar)))))))
(defn _either [a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar) ar ((force b) str)))))
(defn _parser [p]
  (fn [input]
    (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))
(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (partial _map f) parser))
(def +parser _parser)
(def +ignore (partial +map (constantly 'ignore)))
(defn iconj [coll value]
  (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps]
  (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))
(defn +or [p & ps]
  (reduce _either p ps))
(defn +opt [p]
  (+or p (_empty nil)))
(defn +star [p]
  (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))
; ---------- end of the lib ----------
(defn +word [word] (apply +seq (mapv (comp +char str) word)))
(defn +dictionary [& words] (+str (apply +or (mapv +word words))))

(def *digit (+char "0123456789"))
(def *number (+plus *digit))
(def *constant (+map (comp Constant read-string)
                     (+str (+seqf concat
                                  (+seqf cons (+opt (+char "-")) *number)
                                  (+opt (+seqf cons (+char ".") (+opt *number)))))))
(def *var (+map (comp Variable str) (+char "xyz")))
(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))

(defn make-dict [map] (sort (comp - compare) (mapv key map)))
(defn *operation [map] (+map map (+str (apply +dictionary (make-dict map)))))
(def *obj-operation (*operation object-lexemes))
(defn *function [p]
  (+map (fn [[f & r]] (apply f r))
        (+seqn 1
               (+char "(")
               (+opt (+seqf conj (+seqf cons *ws p (+star (+seqn 0 *ws p))) *ws *obj-operation))
               *ws (+char ")"))))

(def parseObjectSuffix
  (letfn [(*value []
            (delay (+or
                     *constant
                     *var
                     (*function (*value)))))]
    (+parser (+seqn 0 *ws (*value) *ws))))
(def parseObjectInfix
  (letfn [(*parse-value []
            (delay (+or
                     *constant
                     *var
                     (*parse-unary)
                     (*parse-function (*parse-xor)))))
          (*parse-unary [] (+map #((first %) (second %)) (+seq (*operation {"negate" Negate}) *ws (*parse-value))))
          (*parse-function [p] (+seqn 1 (+char "(") *ws p *ws (+char ")")))
          (*parse-op [p op] (+map
                              (fn [args] (reduce (fn [f [op s]] (op f s)) (first args) (second args)))
                              (+seq *ws p (+star (+seq *ws op *ws p)))))
          (*parse-level [level map] (*parse-op (level) (*operation map)))
          (*parse-mult-div [] (*parse-level *parse-value {"*" Multiply "/" Divide}))
          (*parse-plus-minus [] (*parse-level *parse-mult-div {"+" Add "-" Subtract}))
          (*parse-and [] (*parse-level *parse-plus-minus {"&" And}))
          (*parse-or [] (*parse-level *parse-and {"|" Or}))
          (*parse-xor [] (*parse-level *parse-or {"^" Xor}))]
    (+parser (+seqn 0 *ws (*parse-xor) *ws))))