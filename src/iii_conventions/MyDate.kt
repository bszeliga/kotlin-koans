package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) :Comparable<MyDate> {
    override fun compareTo(other: MyDate)= when {
        other.year != year -> year - other.year
        other.month != month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(val start: MyDate, val endInclusive: MyDate):Iterable<MyDate> {
    class DateRangeIterator(val dateRange: DateRange): Iterator<MyDate> {
        var current = dateRange.start

        override fun hasNext(): Boolean {
            return current in dateRange
        }

        override fun next(): MyDate {
            val rv = current

            current = current.nextDay()

            return rv
        }

    }

    override fun iterator(): Iterator<MyDate> {
        return DateRangeIterator(this)
    }

    operator fun contains(date: MyDate): Boolean {
        return start <= date && date <= endInclusive
    }
}

data class RepeatedTimeInterval(val interval: TimeInterval, val num: Int)

operator fun MyDate.plus(interval: TimeInterval) : MyDate = addTimeIntervals(interval, 1)
operator fun MyDate.plus(interval: RepeatedTimeInterval) : MyDate = addTimeIntervals(interval.interval, interval.num)
operator fun TimeInterval.times(num:Int) = RepeatedTimeInterval(this, num)