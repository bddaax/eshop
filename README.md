# Tutorial Pemrograman Lanjut
oleh Brenda Po Lok Fahida

### Refleksi 1

> You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code.

Saat mengerjakan *Tutorial 1* dalam mata kuliah Pemrograman Lanjut ini, saya menerapkan beberapa prinsip *clean code* serta praktik pemrograman yang aman. Struktur kode disusun dengan rapi menggunakan *layered architecture* untuk memastikan setiap komponen memiliki tanggung jawab yang jelas. Terdapat beberapa *package* utama dalam project e-shop ini:

- **Controller** (*ProductController*) → Mengelola permintaan HTTP dan interaksi dengan tampilan.
- **Service** (*ProductService*) → Menangani logika bisnis.
- **Repository** (*ProductRepository*) → Mengelola penyimpanan dan pengambilan data.
- **Model** (*Product*) → Merepresentasikan data produk.

Konvensi penamaan dalam kode dibuat jelas dan deskriptif. Nama kelas seperti *ProductController*, *ProductService*, dan *ProductRepository* sudah mencerminkan perannya masing-masing. Metode seperti *create()*, *findAll()*, dan *update()* dinamai sesuai dengan fungsinya agar lebih mudah dipahami. Selain itu, pemilihan nama variabel seperti *productData* dan *productId* juga disesuaikan agar dapat menggambarkan isi variabel dengan jelas.

Dari segi keamanan, aplikasi e-shop ini menggunakan metode POST yang tepat saat memodifikasi data, mengikuti prinsip REST. Formulir yang dibuat dengan Thymeleaf memiliki atribut `th:action`, yang secara otomatis memberikan perlindungan terhadap serangan CSRF. Selain itu, enkapsulasi data diterapkan dengan baik. Atribut dalam kelas *Product* dibuat *private* dan hanya dapat diakses melalui *getter* dan *setter*, sementara daftar produk dalam repository tidak dapat diakses langsung dari luar, sehingga meningkatkan keamanan aplikasi.


### Refleksi 2

> After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors? 

Saya merasa bahwa unit test adalah bagian yang sangat penting dalam pengembangan perangkat lunak karena membantu memastikan bahwa setiap bagian kode bekerja sesuai dengan yang diharapkan. Dengan adanya unit test, kesalahan dapat dideteksi lebih awal, sehingga meminimalkan risiko bug yang muncul saat aplikasi dijalankan. Selain itu, unit test juga mempermudah proses refactoring karena kita dapat langsung mengetahui apakah perubahan yang dilakukan memengaruhi fungsionalitas yang sudah ada.

Jumlah unit test yang diperlukan bergantung pada kompleksitas kelas tersebut. Jika kelas memiliki banyak metode dengan berbagai skenario eksekusi, maka jumlah unit test yang dibutuhkan akan lebih banyak. Secara umum, kita harus membuat unit test untuk setiap metode yang memiliki logika bisnis penting dan kemungkinan jalur eksekusi yang berbeda. Unit test juga harus mencakup skenario positif (happy path) dan skenario negatif, seperti input yang tidak valid atau kondisi yang tidak terduga. Selain itu, unit test harus mencakup edge cases, yaitu kondisi ekstrem yang mungkin jarang terjadi tetapi bisa menyebabkan kesalahan jika tidak ditangani dengan baik.

Untuk mengetahui apakah unit test yang dibuat sudah cukup, salah satu cara yang dapat digunakan adalah dengan mengukur code coverage. Code coverage menunjukkan sejauh mana kode sumber telah diuji oleh unit test, biasanya dalam bentuk persentase. Namun, meskipun memiliki 100% code coverage, itu tidak berarti bahwa kode kita bebas dari bug atau kesalahan. Code coverage hanya menunjukkan bahwa kode telah dieksekusi, tetapi tidak menjamin bahwa logika di dalamnya sudah benar. Misalnya, sebuah tes mungkin hanya menjalankan kode tanpa benar-benar memverifikasi apakah hasilnya sesuai dengan yang diharapkan. Oleh karena itu, selain mengejar code coverage yang tinggi, kita juga harus memastikan bahwa unit test memiliki asersi yang kuat untuk memvalidasi output.

> Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner! 

Pembuatan kelas pengujian fungsional baru yang memiliki prosedur setup dan variabel instance yang sama dengan *CreateProductFunctionalTest.java* berpotensi mengurangi kualitas kode karena adanya duplikasi kode. Duplikasi ini dapat menyebabkan kode menjadi lebih sulit dipelihara dan rentan terhadap inkonsistensi jika terjadi perubahan pada prosedur setup di salah satu kelas, tetapi tidak diperbarui di kelas lainnya. Selain itu, kode yang berulang juga bertentangan dengan prinsip *DRY* (Don't Repeat Yourself), yang merupakan salah satu prinsip utama dalam clean code. Untuk meningkatkan kebersihan kode, sebaiknya prosedur setup yang digunakan oleh beberapa kelas pengujian dipindahkan ke dalam kelas induk (*superclass*) atau dibuat sebagai utilitas yang dapat digunakan kembali. Dengan cara ini, setiap kelas pengujian hanya perlu mewarisi atau memanggil metode setup tersebut tanpa perlu menuliskannya ulang. Selain itu, jika terdapat kesamaan dalam pola pengujian, pendekatan berbasis parameterisasi menggunakan *JUnit Parameterized Tests* atau *Test Factory* juga dapat diterapkan untuk mengurangi duplikasi dan meningkatkan modularitas kode. Hal ini tidak hanya meningkatkan keterbacaan dan efisiensi kode, tetapi juga mempermudah pemeliharaan serta memastikan bahwa setiap perubahan pada prosedur setup atau mekanisme pengujian dapat diterapkan secara konsisten di seluruh suite pengujian.
