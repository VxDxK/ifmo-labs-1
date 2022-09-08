<?php
//session_start();
if (!isset($_SESSION['attempts'])) {
    $_SESSION['attempts'] = array();
}
$arr = $_SESSION['attempts'];
$len = count($arr);
echo ("<table>");
echo "
<tr>
    <th>X</th>
    <th>Y</th>
    <th>R</th>
    <th>Hit status</th>
    <th>Time</th>
    <th>Processing time</th>
</tr>";

foreach ($_SESSION['attempts'] as $iterator){
    echo "<tr>";
        foreach ($iterator as $val){
            echo "<th>";
            echo $val;
            echo "</th>";
        }
    echo "</tr>";
}

echo ("</table>");