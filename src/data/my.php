<?php
print_r($_SERVER);
var_dump(ini_get("foo.bar"));
ini_set("bar.for", "hihi");
var_dump(ini_get("bar.for"));
var_dump($_COOKIE);
var_dump($_SESSION);
var_dump($_GET);
var_dump($_POST);
sleep(1);
echo time() . "\n\n\n";
?>
