<?php

require 'koneksi.php';

$response = [];

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $username = $_POST["username"];
    $password = md5($_POST["password"]);

    $CekUsername = mysqli_query($conn, "SELECT * FROM user WHERE username = '$username'");
    $CekPassword = mysqli_query($conn, "SELECT * FROM user WHERE password = '$password'");

    for ($i = 1; $i <= 3; $i++) {
        if (!mysqli_num_rows($CekUsername) && $i == 1) {
            $response += [
                "Kode" => 0,
                "PesanUsername" => "Username Tidak Terdaftar"
            ];
        } else if (!mysqli_num_rows($CekPassword) && $i == 2) {
            $response += [
                "Kode" => 0,
                "PesanPassword" => "Password Anda Salah"
            ];
        } else if (mysqli_num_rows($CekUsername) && mysqli_num_rows($CekPassword) && $i == 3) {
            $response += [
                "Kode" => 1,
                "Pesan" => "Login Berhasil"
            ];
        }
    }
} else {
    $response += [
        "Kode" => 0,
        "Pesan" => "Invalid Request"
    ];
}

echo json_encode($response);
mysqli_close($conn);
