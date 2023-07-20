package com.topcutlawn.Activity.ScheduleActivity

data class ScheduleServiceModel(
    val booked_date: List<BookedDate>,
    val message: String,
    val status: String
){
    data class BookedDate(
        val date: String
    )
}