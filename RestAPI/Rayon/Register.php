<?php

require 'koneksi.php';

$response = [];

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $email = $_POST["email"];
    $username = $_POST["username"];
    $password = md5($_POST["password"]);

    $CekUsername = mysqli_query($conn, "SELECT * FROM user WHERE username = '$username'");
    $CekEmail = mysqli_query($conn, "SELECT * FROM user WHERE email = '$email'");

    for ($i = 1; $i <= 3; $i++) {
        if (mysqli_num_rows($CekUsername) && $i == 1) {
            $response += [
                "Kode" => 0,
                "PesanUsername" => "Username Sudah Terdaftar"
            ];
        } else if (mysqli_num_rows($CekEmail) && $i == 2) {
            $response += [
                "Kode" => 0,
                "PesanEmail" => "Email Sudah Terdaftar"
            ];
        } else if (!mysqli_num_rows($CekUsername) && !mysqli_num_rows($CekEmail) && $i == 3) {
            $status = 1;
            $code_ferifikasi = 1;
            $active = 1;
            $waktu = null;
            $level = 'user';

            $CekInsert = mysqli_query($conn, "INSERT INTO user VALUES('', '$username', '$email', '$password', '$status', '$code_ferifikasi', '$active', '$waktu', '$level')");

            if (mysqli_affected_rows($conn) > 0) {
                $response += [
                    "Kode" => 1,
                    "Pesan" => "Data Berhasil Di Tambahkan"
                ];
            } else {
                $response += [
                    "Kode" => 0,
                    "Pesan" => "Terjadi Kesalahan Pada Server"
                ];
            }
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
