package dates_between_start_and_end

import java.util.*
import kotlin.NoSuchElementException

fun main() {
    iterateOverDateRange(Date(2020, 2, 1), Date(2020, 2, 20)) {
        println(it.toString())
    }
}

class DateRange(val start: Date, val end: Date) : Iterable<Date> {
    override fun iterator(): Iterator<Date> {
        return object : Iterator<Date> {
            var current: Date = start

            override fun next(): Date {
                if (!hasNext()) throw NoSuchElementException()
                val result = current
                current = current.followingDate()
                return result
            }

            override fun hasNext(): Boolean = current <= end
        }
    }
}

fun iterateOverDateRange(firstDate: Date, secondDate: Date, handler: (Date) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

fun Date.followingDate(): Date {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth)
    val millisecondsInADay = 24 * 60 * 60 * 1000L
    val timeInMillis = c.timeInMillis + millisecondsInADay
    val result = Calendar.getInstance()
    result.timeInMillis = timeInMillis
    return Date(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DATE))
}

data class Date(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<Date> {
    override fun compareTo(other: Date): Int {
        if (year != other.year) return year - other.year
        if (month != other.month) return month - other.month
        return dayOfMonth - other.dayOfMonth
    }
}

operator fun Date.rangeTo(other: Date) = DateRange(this, other)
