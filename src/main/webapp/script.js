window.onload = function (){
    document.getElementById("y-field").addEventListener('input', function (){fieldYValidator()})
    for (let i = 0; i < 9; i++) {
        document.getElementById("butt-" + i).addEventListener('click', function (e){buttonXHandler(e)});
    }
}

function buttonXHandler(eventObj){
    for (let i = 0; i < 9; i++) {
        document.getElementById("butt-" + i).disabled = false;
    }
    document.getElementById("x-field").value = eventObj.target.value;
    eventObj.target.disabled = true;
}

$(document).ready(function() {
    $("img").on("click", function(event) {
        let x = event.pageX - this.offsetLeft;
        let y = event.pageY - this.offsetTop;
        let rPixel = document.querySelector('input[name="r"]:checked').value;

        let trueX = Math.floor(rPixel * (x - 150)/120);
        let trueY = rPixel * -((y - 150))/120;

        if(!validateX(trueX)){
            alert("Invalid x range");
            return;
        }

        if(!validateY(trueY)){
            alert("Invalid y range");
            return;
        }

        document.getElementById("x-field").value = trueX;
        document.getElementById("y-field").value = trueY;
        document.getElementById("submitButt").click();
    });
});


function validateX(x){
    return -5 <= x && x <= 3;
}

function validateY(y){
    return -3 <= y && y <= 5;
}


function fieldYValidator(){
    //parseFloat шлак, потому что буквы после числа отрезает
    document.getElementById("y-field").value = document.getElementById("y-field").value.replace(/[^\d.-]/g, '');
    let x = document.getElementById("y-field").value;
    let valid = true;
    for(let i = 0; i < x.length; i++){
        if(i === 0 && x[i] === '-'){
            continue;
        }
        if(i !== 0 && i !== x.length -1 && (x.charCodeAt(i) === 46 || x.charCodeAt(i) === 44)){
            continue;
        }
        if(x.charCodeAt(i) < 48 || x.charCodeAt(i) > 57){
            valid = false;
            break;
        }
    }
    // console.log(`${x} nan: ${valid}`)
    if(!valid){
        document.getElementById("submitButt").disabled = true;
        document.getElementById("y-warning").innerText = "Not a number";
        return;
    }
    let xFloat = parseFloat(x);

    if(xFloat < -3 || xFloat > 5){
        document.getElementById("submitButt").disabled = true;
        document.getElementById("y-warning").innerText = "X should be in [-3; 5] range";

    }else{
        document.getElementById("submitButt").disabled = false;
        document.getElementById("y-warning").innerText = "";
    }

}