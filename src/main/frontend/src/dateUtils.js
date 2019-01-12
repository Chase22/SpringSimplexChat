export function isSameDay(date1, date2) {
    return withoutTime(date1).getTime() === withoutTime(date2).getTime();
}

export function withoutTime(date) {
    date.setHours(0,0,0,0);
    return date;
}