window.onload = function (){
    updateClock();
    setInterval(updateClock, 12 * 1000)
}

function updateClock(){
    let now = new Date();
    let timeString = [now.getHours(), now.getMinutes(), now.getSeconds() < 10 ? ( "0" + now.getSeconds()) : now.getSeconds()].join(':');
    let dateString = [now.getDate(), now.getMonth() + 1, now.getFullYear()].join('.');
    document.getElementById('clock').innerHTML = [dateString, timeString].join(' | ');
    // setTimeout(updateClock, 1000);
}

