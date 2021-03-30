### Домашнее задание 5. Вычисление в различных типах

1. Добавьте в программу разбирающую и вычисляющую выражения поддержку различных типов. ✅

   - Первым аргументом командной строки программа должна принимать указание на тип, в котором будут производится вычисления:

     | Опция | Тип          |
     | ----- | ------------ |
     | `-i`  | `int`        |
     | `-d`  | `double`     |
     | `-bi` | `BigInteger` |

   - Вторым аргументом командной строки программа должна принимать выражение для вычисления.

   - Реализация не должна содержать [непроверяемых преобразований типов](http://docs.oracle.com/javase/specs/jls/se8/html/jls-5.html#jls-5.1.9).

   - Реализация не должна использовать аннотацию `@SuppressWarnings`.

2. При выполнении задания следует обратить внимание на легкость добавления новых типов и операций.

**Модификации:**

- _Базовая_ ✅

  - Класс `GenericTabulator` должен реализовывать интерфейс `Tabulator` и сроить трехмерную таблицу значений заданного выражения.
    - `mode` – режим вычислений:
      - `i` – вычисления в `int` с проверкой на переполнение;
      - `d` – вычисления в `double` без проверки на переполнение;
      - `bi` – вычисления в `BigInteger`.
    - `expression` – выражение, для которого надо построить таблицу;
    - `x1`, `x2` – минимальное и максимальное значения переменной `x` (включительно)
    - `y1`, `y2`, `z1`, `z2` – аналогично для `y` и `z`.
    - Результат: элемент `result[i][j][k]` должен содержать значение выражения для `x = x1 + i`, `y = y1 + j`, `z = z1 + k`. Если значение не определено (например, по причине переполнения), то соответствующий элемент должен быть равен `null`.

- _CmmUls_ ✅

  - Реализовать операции из модификации *Cmm*.
  - Дополнительно реализовать поддержку режимов:
    - `u` – вычисления в `int` без проверки на переполнение;
    - `l` – вычисления в `long` без проверки на переполнение;
    - `s` – вычисления в `s` без проверки на переполнение.