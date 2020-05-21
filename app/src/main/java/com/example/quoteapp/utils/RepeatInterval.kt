package com.example.quoteapp.utils

enum class RepeatInterval(val interval: Int){
    INTERVAL_0(1),
    INTERVAL_1(3),
    INTERVAL_2(6),
    INTERVAL_3(12),
    INTERVAL_4(24);

    companion object{
        fun byInterval(interval: Int): RepeatInterval {
            for (i in values()) {
                if (interval == i.interval) {
                    return i
                }
            }
            return INTERVAL_0
        }

        fun byPosition(position: Int): RepeatInterval{
            for (i in values()){
                if (position == i.ordinal){
                    return i
                }
            }
            return INTERVAL_0
        }
    }


}

