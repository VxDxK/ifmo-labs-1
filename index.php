<?php
session_start();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>VxDxK web 1</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<header>
    <h1 class="nameHeader">Vadim Ponomarev P32311</h1>
    <h1 class="variantHeader">Variant: 3015</h1>
</header>
<div class="frame">
    <img src="areas.png" alt="">
</div>
<form id="form" action="action.php" method="post">
    <div class="input">
        <div>
            <div class="xandfield">
                <span>X</span>
                <input  class="xfield" id="x-field" onchange="fieldXValidator" type="text" name="x" value="0" size="3" required><br>
            </div>
            <span id="x-warning" class="warning"></span>
        </div>
        <div class="inputBlock">
<!--            <span>Y</span>-->
            <label>Y
                <select name="y" size="1">
                    <option value="-3">-3</option>
                    <option value="-2">-2</option>
                    <option value="-1">-1</option>
                    <option value="0" selected>0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option id="option_y_custom" value="" disabled hidden></option>
                </select>
            </label>
        </div>
        <div class="rDiv">
            <span>R</span>
            <div class="checkboxes">
                <div>
                    <input type="checkbox" id="checkbox-1" name="r" value="1" checked><label>1</label><br>
                </div>
                <div>
                    <input type="checkbox" id="checkbox-2" name="r" value="2"><label>2</label><br>
                </div>
                <div>
                    <input type="checkbox" id="checkbox-3" name="r" value="3"><label>3</label><br>
                </div>
                <div>
                    <input type="checkbox" id="checkbox-4" name="r" value="4"><label>4</label><br>
                </div>

                <div>
                    <input type="checkbox" id="checkbox-5" name="r" value="5"><label>5</label><br>
                </div>
            </div>
        </div>
        <div class="inputBlock">
            <input id="submit" type="submit" value="Send">
        </div>
</form>

<div class="tabl">
    <?php include 'table.php';?>
</div>
<input id="clearButton" type="button" value="Clear table">
<script src="script.js"></script>
</body>
</html>
