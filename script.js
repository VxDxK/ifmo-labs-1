
window.onload = function() {
    for(var i = 1; i < 6; i++){
        document.getElementById("checkbox-" + i).addEventListener('change', function (event){checkboxChecker(event)});
    }
    document.getElementById("x-field").addEventListener('input', function (){fieldXValidator()})
}

function checkboxChecker(eventObj) {
    for(let i = 1; i < 6; i++){
        document.getElementById("checkbox-" + i).checked = false;
    }
    document.getElementById(eventObj.target.id).checked = true;
}


function fieldXValidator(){
    //parseFloat шлак, потому что буквы после числа отрезает
    let x = document.getElementById("x-field").value;

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
        document.getElementById("submit").disabled = true;
        document.getElementById("x-warning").innerText = "Not a number";
        return;
    }
    let xFloat = parseFloat(x);

    if(xFloat < -3 || xFloat > 3){
        document.getElementById("submit").disabled = true;
        document.getElementById("x-warning").innerText = "X should be in [-3; 3] range";

    }else{
        document.getElementById("submit").disabled = false;
        document.getElementById("x-warning").innerText = "";
    }

}

clearButton.onclick = function (){
    window.location.href = "clear.php";
}