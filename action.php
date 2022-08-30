<?php
session_start();
$entryTime = hrtime(true);

if (!isset($_SESSION['attempts'])) {
    $_SESSION['attempts'] = array();
}


$xyrAvailable = true;


if (!isset($_POST['x'])) {
    echo "X is required\r\n";
    $xyrAvailable = false;
}
if (!isset($_POST['y'])) {
    echo "Y is required\r\n";
    $xyrAvailable = false;
}
if (!isset($_POST['r'])) {
    echo "R is required\r\n";
    $xyrAvailable = false;
}


if(!$xyrAvailable){
    http_response_code(400);
}

$x = floatval($_POST['x']);
$y = floatval($_POST['y']);
$r = floatval($_POST['r']);

$quarter = -1;
echo $x . PHP_EOL .$y . PHP_EOL . $r;

if($x > 0){
    if($y > 0){
        $quarter = 1;
    }else if($y < 0){
        $quarter = 4;
    }
}else{
    if($y > 0){
        $quarter = 2;
    }else if($y < 0){
        $quarter = 3;
    }
}
echo $quarter;

$hit = false;

if($quarter == -1){
    $hit = true;
}else if($quarter == 1){
    $hit = (($x*$x + $y*$y) <= ($r/2)*($r/2));
}else if($quarter == 3){
    $hit = ($x >= (-1 *$r)) && ($y >= (-1*$r));
}else if($quarter == 4){
    $hit = ($y>= 1/2*$x - ($r/2));
}


$result = array(
    'x' => $x,
    'y' => $y,
    'r' => $r,
    'hit' => $hit ? "Hit" : "Miss",
    'time' => date("H:i:s d-m-Y"),
    'processing_time' => (hrtime(true) - $entryTime)/1000000
);
$_SESSION['attempts'][] = $result;


http_response_code(200);
header('Location: /web-lab-1/index.php');
