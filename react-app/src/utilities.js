import moment from "moment";
import persianJs from "persianjs";

export function formatNumber(num) {
    return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
}

export function calcTimeLeft(deadline) {
    console.log(deadline);
    deadline = moment(deadline);
    let currentTime = moment();
    let diff = deadline.diff(currentTime);
    // console.log("deadline",deadline)
    // console.log("c",currentTime)
    if (diff <= 0)
        return {timeOver: true, text: 'مهلت تمام شده'};

    let x = [];

    let duration = moment.duration(diff);
    let seconds = duration.seconds();
    x.push({value: seconds, text: 'ثانیه'});

    let minutes = duration.minutes();
    x.push({value: minutes, text: 'دقیقه'});

    let hours = duration.hours();
    x.push({value: hours, text: 'ساعت'});

    let days = duration.days();
    x.push({value: days, text: 'روز'});

    let months = duration.months();
    x.push({value: months, text: 'ماه'});

    let years = duration.years();
    x.push({value: years, text: 'سال'});

    let timeLeft = '';

    for (let i = x.length - 1; i >= 0; i--) {
        let item = x[i];
        if (item.value > 0) {
            if (timeLeft !== '')
                timeLeft += ' و ';
            timeLeft += `${persianJs(item.value).englishNumber()} ${item.text}`;
        }
    }
    return {timeOver: false, text: timeLeft};

}