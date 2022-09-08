<?php
session_start();
$_SESSION['attempts'] = array();

header('Location: ' . $_SERVER['HTTP_REFERER']);
