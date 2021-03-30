### Домашнее задание 9. Линейная алгебра на Clojure

1. Разработайте функции для работы с объектами линейной алгебры, которые представляются следующим образом:
   - скаляры – числа
   - векторы – векторы чисел;
   - матрицы – векторы векторов чисел.
2. Функции над векторами: ✅
   - `v+`/`v-`/`v*` – покоординатное сложение/вычитание/умножение;
   - `scalar`/`vect` – скалярное/векторное произведение;
   - `v*s` – умножение на скаляр.
3. Функции над матрицами: ✅
   - `m+`/`m-`/`m*` – поэлементное сложение/вычитание/умножение;
   - `m*s` – умножение на скаляр;
   - `m*v` – умножение на вектор;
   - `m*m` – матричное умножение;
   - `transpose` – траспонирование;
4. Сложный вариант. ✅
   1. Ко всем функциям должны быть указаны контракты. Например, нельзя складывать вектора разной длины.
   2. Все функции должны поддерживать произвольное число аргументов. Например `(v+ [1 2] [3 4] [5 6])` должно быть равно `[9 12]`.
5. При выполнение задания следует обратить внимание на:
   - Применение функций высшего порядка.
   - Выделение общего кода для операций.

**Модификация:**

- Cuboid ✅
  - Назовем *кубоидом* трехмерную прямоугольную таблицу чисел.
  - Добавьте операции поэлементного сложения (`c+`), вычитания (`c-`), умножения (`c*`) и деления (`cd`) кубоидов. Например, `(с+ [[[1] [2]] [[3] [4]]] [[[5] [6]] [[7] [8]]])` должно быть равно `[[[6] [8]] [[10] [12]]]`.
- Tensor ✅
  - Назовем *тензором* многомерную прямоугольную таблицу чисел.
  - Добавьте операции поэлементного сложения (`t+`), вычитания (`t-`) и умножения (`t*`) тензоров. Например, `(t+ [[1 2] [3 4]] [[5 6] [7 8]])` должно быть равно `[[6 8] [10 12]]`.