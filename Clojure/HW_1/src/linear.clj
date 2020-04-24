(defn sameLength [& args] (every? #(= (count (first args)) (count %1)) args))
(defn allVectors [& args] (every? vector? args))
(defn doOp [f]
  (fn [& args]
    {:pre [(apply allVectors args) (apply sameLength args)]
     :post [(vector? %) (= (count (first args)) (count %))]}
    (apply mapv f args)))
; vector
(def v+ (doOp +))
(def v- (doOp -))
(def v* (doOp *))
(def vd (doOp /))
(defn scalar [& args] (apply + (apply v* args)))
(defn v*s [vec & args] (mapv #(* (apply * args) %1) vec))
(defn det2 [a b i1 i2]
  {:pre [(= (count a) (count b))]
   :post [true]}
  (- (* (nth a i1) (nth b i2)) (* (nth b i1) (nth a i2))))
(defn vect [& args] (reduce #(vector (det2 %1 %2 1 2) (- (det2 %1 %2 0 2)) (det2 %1 %2 0 1)) args))
; matrix
(def m+ (doOp v+))
(def m- (doOp v-))
(def m* (doOp v*))
(def md (doOp vd))
(defn m*s [mat & args] (mapv #(v*s %1 (apply * args)) mat))
(defn m*v [mat vec] (mapv (partial scalar vec) mat))
(defn transpose [mat] (apply mapv vector mat))
(defn m*m [& args] (reduce #(transpose ((fn [x y] (mapv (partial m*v x) (transpose y))) %1 %2)) args))
; cuboid
(def c+ (doOp m+))
(def c- (doOp m-))
(def c* (doOp m*))
(def cd (doOp md))
; tensor

(defn checkTensor [args] (if (every? number? args)
                           true (if (every? vector? args)
                              (if (apply sameLength args)
                                (every? checkTensor (apply mapv vector args)) false) false)))
(defn compareT [args] (or (number? (first args)) (apply sameLength args)))
(defn doTen [f & args]
  {:pre [(compareT args)]}
  (if (vector? (first args))
    (apply mapv (partial doTen f) args)
    (apply f args)))
(defn tensorOp [f & args]
  {:pre [(checkTensor (first args))]}
  (apply doTen f args))

(def t+ (partial tensorOp +))
(def t- (partial tensorOp -))
(def t* (partial tensorOp *))