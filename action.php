<?php
session_start();
if (!isset($_SESSION['attempts'])) {
    $_SESSION['attempts'] = array();
}

$entryTime = hrtime(true);
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

if($x > 3 || $x < -3){
    echo "Thanks for breaking my api, x is not in [-3;3] range";
    http_response_code(400);
    header('Location: ' . $_SERVER['HTTP_REFERER']);
}

if(!is_int($y) || $y < -3 || $y > 6){
    echo "Thanks for breaking my api, y is not in Z[-3;6] range";
    http_response_code(400);
    header('Location: ' . $_SERVER['HTTP_REFERER']);
}

if(!is_int($r) || $r < 0 || $r > 5){
    echo "Thanks for breaking my api, r is not in [0;5] range";
    http_response_code(400);
    header('Location: ' . $_SERVER['HTTP_REFERER']);
}


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
    'hit' => $hit ? "<div class='hit'>Hit</div>" : "<div class='miss'>Miss</div>",
    'time' => date("H:i:s d-m-Y"),
    'processing_time' => (hrtime(true) - $entryTime)/1000000
);
$_SESSION['attempts'][] = $result;


http_response_code(200);
//header('Location: /web-lab-1/index.php');
header('Location: ' . $_SERVER['HTTP_REFERER']);
