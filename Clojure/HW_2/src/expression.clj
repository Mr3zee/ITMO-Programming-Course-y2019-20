(require '[clojure.string :as string])

; constants and vars
(defn constant [val] (fn [vars] val))
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
              "+" add
              "-" subtract
              "*" multiply
              "/" divide
              "negate" negate
              "min" min
              "max" max
              "exp" exp
              "ln" ln
              "avg" avg
              "med" med
              "pw" pw
              "lg" lg
              })
(def vars-function {
           "x" x
           "y" y
           "z" z
           })

(defn parse [lexs vars const]
  (letfn [
          (extract [arg]
                  (cond
                    (list? arg) (let [
                                      [f & args] arg
                                      f' (get lexs (name f))
                                      ]
                                  (apply f' (map extract args)))
                    (symbol? arg) (get vars (name arg))
                    :else (const arg)))
        ] (fn [expression] (extract (read-string expression)))))
(def parseFunction (parse lexemes vars-function constant))
; Objects

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
(def diff (method :diff))

(def Constant)
(def const-proto
  {
   :evaluate  (fn [this _] (_value this))
   :toString  #(format "%.1f" (_value %))
   :diff      (fn [_ _] (Constant 0))
   })
(defn const-constructor [this val] (assoc this :value val))
(def Constant (constructor const-constructor const-proto))
(def ZERO (Constant 0))
(def ONE (Constant 1))

(def variable-proto
  {
   :evaluate  #(get %2 (_name %1))
   :toString  #(_name %)
   :diff      #(if (= (_name %1) %2) ONE ZERO)
   })
(defn variable-constructor [this name] (assoc this :name name))
(def Variable (constructor variable-constructor variable-proto))
(def X (Variable "x"))
(def Y (Variable "y"))
(def Z (Variable "z"))

(def operation-proto
  {
   :diff      (fn [this var] ((diff-rule this) (_args this) var))
   :evaluate  (fn [this vars] (apply (_op this) (mapv #(evaluate % vars) (_args this))))
   :toString  #(str "(" (_name %) " " (string/join " " (mapv toString (_args %))) ")")
   })
(defn operation-constructor [this operation name rule]
  (fn [& args] (assoc this :op operation :args args :name name :diff-rule rule)))
(defn to-diff [args var] (mapv #(diff % var) args))

(def Basic (constructor operation-constructor operation-proto))
(def Add (Basic + "+" (fn [args var] (apply Add (to-diff args var)))))
(def Sum (Basic + "sum" (fn [args var] (apply Sum (to-diff args var)))))
(def Avg (Basic math-avg "avg" (fn [args var] (apply Avg (to-diff args var)))))
(def Subtract (Basic - "-" (fn [args var] (apply Subtract (to-diff args var)))))
(def Negate (Basic - "negate" (fn [args var] (apply Subtract (to-diff args var)))))
(def Multiply (Basic * "*"
                     (fn [args var] (reduce
                                      (fn [a b]
                                        (Add
                                          (Multiply (diff a var) b)
                                          (Multiply (diff b var) a)
                                          )) args))))
(def Divide (Basic div "/"
                   (fn [args var] (reduce
                                     (fn [a b]
                                       (Divide
                                         (Subtract
                                           (Multiply (diff a var) b)
                                           (Multiply (diff b var) a)
                                           )
                                         (Multiply b b)
                                         )) args))))

(def object-lexemes
  {
   "+" Add
   "-" Subtract
   "*" Multiply
   "/" Divide
   "negate" Negate
   "avg" Avg
   "sum" Sum
   })
(def vars-object {
                    "x" X
                    "y" Y
                    "z" Z
                    })
(def parseObject (parse object-lexemes vars-object Constant))