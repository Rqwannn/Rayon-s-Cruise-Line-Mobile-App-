<?php

require 'koneksi.php';

$query = mysqli_query($conn, "SELECT * FROM data_kapal ORDER BY id DESC");
$cek = mysqli_affected_rows($conn);

$wrapper = [];

if ($cek > 0) {
    foreach ($query as $result) {
        $wrapper[] = $result;
    }

    $data = [
        "Data" => $wrapper,
        "Kode" => 1,
        "Pesan" => "Data Tersedia"
    ];
} else {
    $data = [
        "Kode" => 0,
        "Pesan" => "Data Tidak Tersedia"
    ];
}

echo json_encode($data);
mysqli_close($conn);
