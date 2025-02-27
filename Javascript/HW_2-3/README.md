### Домашнее задание 7. Объектные выражения на JavaScript

1. Разработайте классы `Const`, `Variable`, `Add`, `Subtract`, `Multiply`, `Divide`, `Negate` для представления выражений с одной переменной. ✅
   1. Пример описания выражения`2x-3`:
      ```
      let expr = new Subtract(
          new Multiply(
              new Const(2),
              new Variable("x")
          ),
          new Const(3)
      );
      ```

   2. Метод `evaluate(x)` должен производить вычисления вида: При вычислении такого выражения вместо каждой переменной подставляется значение `x`, переданное в качестве параметра функции `evaluate` (на данном этапе имена переменных игнорируются). Таким образом, результатом вычисления приведенного примера должно стать число 7. ✅

   3. Метод `toString()` должен выдавать запись выражения в [обратной польской записи](http://ru.wikipedia.org/wiki/Обратная_польская_запись). Например, `expr.toString()` должен выдавать `2 x * 3 -`. ✅

2. **Сложный вариант**. ✅
   Метод `diff("x")` должен возвращать выражение, представляющее производную исходного выражения по переменной `x`. Например, `expr.diff("x")` должен возвращать выражение, эквивалентное `new Const(2)` (выражения `new Subtract(new Const(2), new Const(0))` и
   
   ```
   new Subtract(
       new Add(
           new Multiply(new Const(0), new Variable("x")),
           new Multiply(new Const(2), new Const(1))
       )
       new Const(0)
   )
   ```
так же будут считаться правильным ответом).
   
Функция `parse` должна выдавать разобранное объектное выражение.
   
3. **Бонусный вариант.** ❌
    Требуется написать метод `simplify()`, производящий вычисления константных выражений. Например,
    
    `parse("x x 2 - * 1 *").diff("x").simplify().toString()`
    
    должно возвращать `x x 2 - +`.

4. При выполнении задания следует обратить внимание на:

   - Применение инкапсуляции.
   - Выделение общего кода для операций.

**Модификации:**

*PowLog*. Дополнительно реализовать поддержку: ✅

- бинарных операций:
  - `Power` (`pow`) – возведение в степень, `2 3 pow` равно 8;
  - `Log` (`log`) – логарифм абсолютного значения аргумента по абсолютному значению основания `-2 -8 log` равно 3;

### Домашнее задание 8. Обработка ошибок на JavaScript

1. Добавьте в предыдущее домашнее задание функцию `parsePrefix(string)`, разбирающую выражения, задаваемые записью вида `(- (* 2 x) 3)`. Если разбираемое выражение некорректно, метод `parsePrefix` должен бросать человеко-читаемое сообщение об ошибке. ✅
2. Добавьте в предыдущее домашнее задание метод `prefix()`, выдающий выражение в формате, ожидаемом функцией `parsePrefix`. ✅
3. При выполнении задания следует обратить внимание на:
   - Применение инкапсуляции.
   - Выделение общего кода для бинарных операций.
   - Обработку ошибок.
   - Минимизацию необходимой памяти.

**Модификации:**

*PostfixSumexpSoftmax*. Дополнительно реализовать поддержку: ✅

- выражений в постфиксной записи: `(2 3 +)` равно 5
- унарных операций:
  - `Sumexp` (`sumexp`) – сумма экспонент, `(8 8 9 sumexp)` примерно равно 14065;
  - `Softmax` (`softmax`) – [softmax](https://ru.wikipedia.org/wiki/Softmax) первого аргумента, `(1 2 3 softmax)` примерно 0.09;