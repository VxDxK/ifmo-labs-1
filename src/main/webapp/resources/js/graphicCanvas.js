const submit_button = document.getElementById("form:submit-button")
const canvas = document.getElementById("big_canvas");
const context = canvas.getContext("2d");
const canvas_scale = 30;
context.translate(canvas.width/2, canvas.height/2);
context.scale(1,-1);

canvas.addEventListener("click", function(e) {
    getCursorPosition(canvas, e)
});

function getR() {
    let r = 0;

    const form = document.getElementById('form');

    if (form) {
        const formData = new FormData(form);
        const parsedR = parseFloat(formData.get('form:r'));
        r = (isNaN(parsedR) ? 0 : parsedR);
    }

    return r;
}

function getCursorPosition(canvas, event) {
    const rect = canvas.getBoundingClientRect();

    // calculate click place
    let x = (event.clientX - rect.left) - canvas.width/2;
    let y = canvas.height/2 - (event.clientY - rect.top);

    // normalise click place
    x = x / canvas_scale;
    y = y / canvas_scale;

    document.getElementById("form:x").value = x.toFixed(2);
    document.getElementById("form:y").value = y.toFixed(2);
    submit_button.click();
    document.getElementById("form:x").selectedIndex = 0;
    document.getElementById("form:y").value = -3;

}

function redraw_axes(){
    context.fillStyle = "rgb(1, 1, 1)"

    // y - axe
    context.beginPath();
    context.moveTo(0, -canvas.height/2 + 1);
    context.lineTo(0, canvas.height/2 - 1);
    context.closePath();
    context.stroke();

    // x - axe
    context.beginPath();
    context.moveTo(-canvas.width/2 + 1, 0);
    context.lineTo(canvas.width/2 - 1, 0);
    context.closePath();
    context.stroke();
}
function redraw_labels(r_value){
    context.fillStyle = "rgb(1, 1, 1)"

    // left
    context.beginPath();
    context.moveTo(-Math.floor(r_value * canvas_scale), -5);
    context.lineTo(-Math.floor(r_value * canvas_scale), 5);
    context.stroke();

    // half-left
    context.beginPath();
    context.moveTo(-Math.floor(r_value/2 * canvas_scale), -5);
    context.lineTo(-Math.floor(r_value/2 * canvas_scale), 5);
    context.stroke();

    // right
    context.beginPath();
    context.moveTo(Math.floor(r_value * canvas_scale), -5);
    context.lineTo(Math.floor(r_value * canvas_scale), 5);
    context.stroke();

    // half-right
    context.beginPath();
    context.moveTo(Math.floor(r_value/2 * canvas_scale), -5);
    context.lineTo(Math.floor(r_value/2 * canvas_scale), 5);
    context.stroke();

    // top
    context.beginPath();
    context.moveTo(-5, Math.floor(r_value * canvas_scale));
    context.lineTo(5, Math.floor(r_value * canvas_scale));
    context.stroke();

    // half-top
    context.beginPath();
    context.moveTo(-5, Math.floor(r_value/2 * canvas_scale));
    context.lineTo(5, Math.floor(r_value/2 * canvas_scale));
    context.stroke();

    // bottom
    context.beginPath();
    context.moveTo(-5, -Math.floor(r_value * canvas_scale));
    context.lineTo(5, -Math.floor(r_value * canvas_scale));
    context.stroke();

    // half-bottom
    context.beginPath();
    context.moveTo(-5, -Math.floor(r_value/2 * canvas_scale));
    context.lineTo(5, -Math.floor(r_value/2 * canvas_scale));
    context.stroke();
}
function redraw_points(){
    context.save();
    let table = document.getElementById("maintable");
    for (let row of table.rows)
    {
        try{

            if(row.cells[0].innerText === "" || row.cells[1].innerText === "" || row.cells[4].innerText===""){
                continue;
            }

            let x = Number(row.cells[0].innerText);
            let y = Number(row.cells[1].innerText);
            console.log(row);
            console.log(row.cells[3].innerText);
            if (row.cells[3].innerText === "Hit"){
                context.fillStyle = "#00ff00";
            }else {
                context.fillStyle = "#ff0000";
            }

            context.beginPath(); //Start path
            context.arc(x * canvas_scale, y * canvas_scale,
                4, 0, Math.PI * 2
            );
            context.fill();

        } catch (e){
            console.log(e);
        }

    }

    context.restore();
}
function redraw_canvas(r_value){

    context.clearRect(-canvas.width/2, -canvas.height/2, canvas.width, canvas.height);


    context.beginPath();
    context.fillStyle = "white";
    context.fillRect(-canvas.width/2, -canvas.height/2, canvas.width, canvas.height);
    context.closePath();
    context.fillStyle = "#e59def";

    // 1 quarter
    context.beginPath();
    context.moveTo(0, 0);
    context.lineTo(0, Math.floor(r_value * canvas_scale)/2);
    context.lineTo(Math.floor(r_value * canvas_scale)/2, 0);
    context.closePath();
    context.fill();

    // 3 quarter
    context.beginPath();
    context.moveTo(0, 0);
    context.lineTo(0, -Math.floor(r_value * canvas_scale));
    context.lineTo(-Math.floor(r_value * canvas_scale)/2, -Math.floor(r_value * canvas_scale));
    context.lineTo(-Math.floor(r_value * canvas_scale)/2, 0);
    context.closePath();
    context.fill();

    //4 quarter
    context.beginPath();
    context.arc(0,0, Math.floor(r_value * canvas_scale), 1/2 * Math.PI + Math.PI, 2 * Math.PI);
    context.moveTo(0, 0);
    context.lineTo(0, -Math.floor(r_value * canvas_scale));
    context.lineTo(Math.floor(r_value * canvas_scale), 0);
    context.closePath();
    context.fill();

    // axes
    redraw_axes();

    // labels
    redraw_labels(r_value);

    //points
    redraw_points();
}

function updater(){
    const r = getR();
    redraw_canvas(r);
}

window.onload = function (){
    if (getR() === 0){
        redraw_canvas(1);
    }else{
        redraw_canvas(getR());
    }
}

