; constants and vars
(defn constant [val] (fn [vars] val))
(defn variable [name] (fn [vars] (get vars name)))
(def x (variable "x"))
(def y (variable "y"))
(def z (variable "z"))

; math
(defn div [& args] (reduce #(/ (double %1) %2) args))
(def math-ln #(div (Math/log (Math/abs %1)) (Math/log Math/E)))
(defn math-avg [& args] (let [c (count args)] (div (apply + args) c)))
(def math-med #(let [k (div (count %&) 2)] (nth (sort %&) k)))

; operations
(defn basic-op [f & args] (fn [vars] (apply f ((apply juxt args) vars))))
(def add (partial basic-op +))
(def subtract (partial basic-op -))
(def multiply (partial basic-op *))
(def divide (partial basic-op div))
(def negate (partial basic-op #(- %1)))
(def min (partial basic-op clojure.core/min))
(def max (partial basic-op clojure.core/max))
(def exp (partial basic-op #(Math/exp %1)))
(def ln (partial basic-op math-ln))
(def avg (partial basic-op math-avg))
(def med (partial basic-op math-med))
; todo hw_8 js name class Error

; parse
(def lexemes {"+" add "-" subtract "*" multiply "/" divide "negate" negate
              "min" min "max" max "exp" exp "ln" ln "avg" avg "med" med})
(def vars {"x" x "y" y "z" z})
(defn extract [arg]
  (cond
    (list? arg) (let [[f & args] arg f' (get lexemes (name f))] (apply f' (map extract args)))
    (symbol? arg) (get vars (name arg))
    :else (constant arg)))
(defn parseFunction [expression] (extract (read-string expression)))