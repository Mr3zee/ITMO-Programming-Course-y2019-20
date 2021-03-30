### Домашнее задание 3. Очередь на массиве

1. Найдите инвариант структуры данных «[очередь](http://ru.wikipedia.org/wiki/Очередь_(программирование))». Определите функции, которые необходимы для реализации очереди. Найдите их пред- и постусловия, при условии что очередь не содержит `null`.
2. Реализуйте классы, представляющие циклическую очередь с применением массива. ✅
   - Класс `ArrayQueueModule` должен реализовывать один экземпляр очереди с использованием переменных класса.
   - Класс `ArrayQueueADT` должен реализовывать очередь в виде абстрактного типа данных (с явной передачей ссылки на экземпляр очереди).
   - Класс `ArrayQueue` должен реализовывать очередь в виде класса (с неявной передачей ссылки на экземпляр очереди).
   - Должны быть реализованы следующие функции (процедуры) / методы:
     - `enqueue` – добавить элемент в очередь;
     - `element` – первый элемент в очереди;
     - `dequeue` – удалить и вернуть первый элемент в очереди;
     - `size` – текущий размер очереди;
     - `isEmpty` – является ли очередь пустой;
     - `clear` – удалить все элементы из очереди.
   - Инвариант, пред- и постусловия записываются в исходном коде в виде комментариев.
   - Обратите внимание на инкапсуляцию данных и кода во всех трех реализациях.
3. Напишите тесты к реализованным классам. ✅

**Модификации:**

- _ToArray_ (простая) ✅
  - Реализовать метод `toArray`, возвращающий массив, содержащий элементы, лежащие в очереди в порядке от головы к хвосту.
  - Исходная очередь должна остаться неизменной
  - Дублирования кода быть не должно
- _ToStr_ (простая) ✅
  - Реализовать метод `toStr`, возвращающий строковое представление очереди в виде ‘`[`’ *голова* ‘`,`’ … ‘`,`’ *хвост* ‘`]`‘
- _Deque_ (сложная) ✅
  - Реализовать методы
    - `push` – добавить элемент в начало очереди
    - `peek` – вернуть последний элемент в очереди
    - `remove` – вернуть и удалить последний элемент из очереди