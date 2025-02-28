# Tutorial Pemrograman Lanjut
oleh Brenda Po Lok Fahida



**Modul 3: Maintainability & OO Principles**
---

<details><summary>Refleksi 1</summary>


> **Explain what principles you apply to your project!**

### 1. Prinsip-prinsip SOLID yang Diterapkan pada Proyek

**Single Responsibility Principle (SRP)**
: A class should have only one reason to change, meaning it should have only one responsibility. This makes the class more maintainable, as changes to one aspect of the system won't affect unrelated aspects.
- **Implementasi**: Memisahkan `CarController` dari `ProductController` menjadi kelas terpisah agar masing-masing memiliki tanggung jawab yang jelas.
- **Alasan**: Dengan memisahkan fungsi-fungsi yang berbeda ke dalam kelas masing-masing, kode menjadi lebih modular dan mudah dipelihara. Setiap perubahan dalam domain tertentu tidak akan berdampak pada domain lain.


**Open/Closed Principle (OCP)**
: Software entities (classes, modules, functions, etc.) should be open for extension but closed for modification. This encourages the use of interfaces and abstract classes, allowing you to extend the behavior of a system without modifying existing code. This promotes code stability.
- **Implementasi**: Menggunakan interface `CarService` dengan implementasi `CarServiceImpl`.
- **Alasan**: Dengan menggunakan interface, kita bisa menambahkan fitur baru atau mengganti implementasi tanpa mengubah kode yang sudah ada, sehingga mengurangi risiko bug dan meningkatkan fleksibilitas sistem.


**Liskov Substitution Principle (LSP)**
: Objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program. This ensures that subtypes can be used interchangeably with their base types, promoting polymorphism and preventing unexpected behaviors.
- **Implementasi**: Memastikan `CarServiceImpl` mengimplementasikan interface `CarService` dengan benar tanpa mengubah perilaku yang diharapkan.
- **Alasan**: Dengan mengikuti prinsip ini, setiap implementasi dari suatu interface dapat digunakan secara konsisten tanpa menyebabkan error atau perubahan perilaku yang tidak diinginkan.


**Interface Segregation Principle (ISP)**
: A class should not be forced to implement interfaces it does not use. This principle promotes the creation of smaller, more specific interfaces, preventing classes from being burdened with methods they don't need.
- **Implementasi**: Memisahkan interface `CarService` agar hanya berisi metode yang relevan untuk operasi yang berkaitan dengan mobil (`Car`).
- **Alasan**: Dengan membagi interface menjadi lebih spesifik, setiap kelas hanya perlu mengimplementasikan metode yang benar-benar dibutuhkan, sehingga menghindari implementasi metode yang tidak relevan.


**Dependency Inversion Principle (DIP)**
: High-level modules should not depend on low-level modules. Both should depend on abstractions. This principle encourages the use of dependency injection and inversion of control to decouple components, making the system more flexible and easier to maintain.
- **Implementasi**: Menggunakan constructor injection untuk mendeklarasikan dependensi pada interface daripada implementasi konkret.
- **Alasan**: Menggunakan abstraksi dalam dependensi membantu mengurangi ketergantungan langsung antara modul, sehingga mempermudah pengujian dan memungkinkan perubahan implementasi tanpa mengganggu modul yang menggunakan layanan tersebut.

</details>



<details><summary>Refleksi 2</summary>

> **Explain the advantages of applying SOLID principles to your project with examples.**

### 2. Keuntungan Menerapkan Prinsip SOLID pada Proyek

**Single Responsibility Principle (SRP)**
- **Manfaat**: Kode lebih mudah dipahami, dipelihara, dan diuji karena setiap kelas memiliki tanggung jawab yang jelas.
- **Contoh**: Dengan memisahkan `CarController` dan `ProductController`, perubahan pada fitur car tidak akan mempengaruhi fitur produk.


**Open/Closed Principle (OCP)**
- **Manfaat**: Pengembangan fitur baru lebih mudah dilakukan tanpa harus mengubah kode yang sudah stabil.
- **Contoh**: Jika ingin menambahkan suatu fitur untuk car, cukup membuat decorator `(fitur)CarServiceImpl` tanpa perlu memodifikasi `CarServiceImpl`.


**Liskov Substitution Principle (LSP)**
- **Manfaat**: Memastikan fleksibilitas dan modularitas kode, sehingga implementasi baru dapat digunakan tanpa risiko error yang tidak terduga.
- **Contoh**: Jika memiliki subclass dari `CarServiceImpl`, sistem tetap dapat berjalan dengan baik tanpa memerlukan perubahan tambahan di kode yang menggunakan `CarService`.


**Interface Segregation Principle (ISP)**
- **Manfaat**: Mencegah kelas memiliki metode yang tidak relevan, sehingga mengurangi kompleksitas kode.
- **Contoh**: Interface `CarService` hanya memiliki metode yang berhubungan dengan operasi car, sedangkan operasi produk ditangani oleh `ProductService`.


**Dependency Inversion Principle (DIP)**
- **Manfaat**: Mempermudah pengujian, mengurangi ketergantungan antar modul, dan meningkatkan fleksibilitas sistem.
- **Contoh**: `CarController` tidak langsung bergantung pada `CarServiceImpl`, melainkan pada `CarService`, memungkinkan penggunaan mock service dalam pengujian.

</details>



<details><summary>Refleksi 3</summary>

> **Explain the disadvantages of not applying SOLID principles to your project with examples.**

### 3. Kerugian Tidak Menerapkan Prinsip SOLID pada Proyek

**Single Responsibility Principle (SRP)**
- **Dampak**: Kode menjadi sulit dipelihara, sulit diuji, dan lebih rentan terhadap bug.
- **Contoh**: Jika `ProductController` menangani logika car dan produk sekaligus, perubahan pada salah satu fitur dapat menyebabkan error di fitur lainnya.

**Open/Closed Principle (OCP)**
- **Dampak**: Penambahan fitur baru mengharuskan perubahan di kelas yang sudah ada, meningkatkan risiko bug.
- **Contoh**: Jika fitur baru ditambahkan ke `CarServiceImpl`, maka seluruh sistem yang menggunakan `CarServiceImpl` harus diuji ulang sepenuhnya.

**Liskov Substitution Principle (LSP)**
- **Dampak**: Implementasi turunan dapat menyebabkan error jika tidak sesuai dengan ekspektasi dari superclass.
- **Contoh**: Jika subclass dari `CarServiceImpl` tidak mendukung metode yang sama seperti `CarServiceImpl`, sistem dapat mengalami error saat menggantikan instance-nya.

**Interface Segregation Principle (ISP)**
- **Dampak**: Kelas harus mengimplementasikan metode yang tidak diperlukan, meningkatkan kompleksitas kode.
- **Contoh**: Jika `CarService` memiliki metode yang juga digunakan oleh produk, maka implementasi layanan car akan memiliki metode yang tidak relevan.

**Dependency Inversion Principle (DIP)**
- **Dampak**: Sulit mengganti implementasi dan meningkatkan ketergantungan antar modul, sehingga menyulitkan pengujian.
- **Contoh**: Jika `CarController` langsung bergantung pada `CarServiceImpl`, maka penggantian implementasi atau pembuatan unit test menjadi lebih sulit.

</details>



**Modul 2: CI/CD & DevOps**
---

<details><summary>Refleksi 1</summary>

> **List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.**

Selama pengerjaan Tutorial 2 ini, saya mengidentifikasi dan memperbaiki beberapa masalah kualitas kode berikut:

1) **Import Tidak Digunakan (UnnecessaryImport - PMD) dalam HomePageController.java dan ProductController.java**

    - **Masalah:** Penggunaan wildcard import (`import org.springframework.web.bind.annotation.*;`) dianggap tidak perlu karena hanya beberapa anotasi yang digunakan.
    - **Solusi:** Mengganti wildcard import dengan impor eksplisit seperti `@GetMapping`, `@PostMapping`, dll., sehingga hanya dependensi yang diperlukan saja yang disertakan. Hal ini meningkatkan keterbacaan kode serta mengurangi potensi masalah kompatibilitas di masa depan.

</details>

<details><summary>Refleksi 2</summary>

> **Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!**

Menurut saya, implementasi **CI/CD** dalam proyek ini sudah memenuhi definisi **Continuous Integration (CI)** dan **Continuous Deployment (CD)**. Dalam tahap **CI**, saya telah mengintegrasikan beberapa workflow seperti `ci.yml`, `pmd.yml`, dan `scorecard.yml`, yang secara otomatis menjalankan analisis kode statis, pengujian unit, serta pemeriksaan cakupan kode setiap kali ada perubahan yang masuk. Dengan adanya workflow ini, saya dapat memastikan bahwa setiap perubahan diuji sebelum diintegrasikan ke dalam branch utama.

Selain itu, saya menggunakan **JaCoCo** untuk mengukur cakupan kode dalam unit test. Sebelumnya, cakupan kode saya hanya sekitar **43%**, yang berarti ada banyak bagian kode yang belum dieksekusi oleh pengujian. Dengan membuka laporan cakupan kode yang dihasilkan oleh **JaCoCo** (`build/reports/jacoco/test/html/index.html`), saya dapat dengan mudah melihat bagian kode mana saja yang belum tercover dalam unit test. Dalam laporan tersebut, kode yang belum diuji akan ditandai dengan warna **merah**, sementara kode yang sudah teruji akan ditandai dengan warna **hijau**. Dengan informasi ini, saya dapat menambahkan **unit test** yang lebih spesifik untuk mencakup bagian kode yang sebelumnya tidak teruji, sehingga meningkatkan cakupan kode secara keseluruhan.

Untuk aspek **Continuous Deployment (CD)**, saya menggunakan [Koyeb](https://magnetic-fay-bddaax-66a5cbcc.koyeb.app/) sebagai platform untuk otomatisasi deployment. Dengan konfigurasi ini, setiap perubahan yang berhasil melewati tahap **CI** akan langsung diterapkan ke lingkungan produksi tanpa perlu intervensi manual. Hal ini membuat proses deployment menjadi lebih cepat dan efisien, serta meminimalkan risiko kesalahan manusia dalam rilis aplikasi.

Secara keseluruhan, workflow ini telah membantu dalam menjaga kualitas kode serta memastikan aplikasi selalu dalam kondisi stabil sebelum diterapkan di lingkungan produksi.

</details>



**Modul 1: Coding Standards**
---

<details><summary>Refleksi 1</summary>

> You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code.  If you find any mistake in your source code, please explain how to improve your code.

Saat mengerjakan *Tutorial 1* dalam mata kuliah Pemrograman Lanjut ini, saya menerapkan beberapa prinsip *clean code* serta praktik pemrograman yang aman. Struktur kode disusun dengan rapi menggunakan *layered architecture* untuk memastikan setiap komponen memiliki tanggung jawab yang jelas. Terdapat beberapa *package* utama dalam project e-shop ini:

- **Controller** (*ProductController*) → Mengelola permintaan HTTP dan interaksi dengan tampilan.
- **Service** (*ProductService*) → Menangani logika bisnis.
- **Repository** (*ProductRepository*) → Mengelola penyimpanan dan pengambilan data.
- **Model** (*Product*) → Merepresentasikan data produk.

Konvensi penamaan dalam kode dibuat jelas dan deskriptif. Nama kelas seperti *ProductController*, *ProductService*, dan *ProductRepository* sudah mencerminkan perannya masing-masing. Metode seperti *create()*, *findAll()*, dan *update()* dinamai sesuai dengan fungsinya agar lebih mudah dipahami. Selain itu, pemilihan nama variabel seperti *productData* dan *productId* juga disesuaikan agar dapat menggambarkan isi variabel dengan jelas.

Dari segi keamanan, aplikasi e-shop ini menggunakan metode POST yang tepat saat memodifikasi data, mengikuti prinsip REST. Formulir yang dibuat dengan Thymeleaf memiliki atribut `th:action`, yang secara otomatis memberikan perlindungan terhadap serangan CSRF. Selain itu, enkapsulasi data diterapkan dengan baik. Atribut dalam kelas *Product* dibuat *private* dan hanya dapat diakses melalui *getter* dan *setter*, sementara daftar produk dalam repository tidak dapat diakses langsung dari luar, sehingga meningkatkan keamanan aplikasi.

</details>


<details><summary>Refleksi 2</summary>

> After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?

Saya merasa bahwa unit test adalah bagian yang sangat penting dalam pengembangan perangkat lunak karena membantu memastikan bahwa setiap bagian kode bekerja sesuai dengan yang diharapkan. Dengan adanya unit test, kesalahan dapat dideteksi lebih awal, sehingga meminimalkan risiko bug yang muncul saat aplikasi dijalankan. Selain itu, unit test juga mempermudah proses refactoring karena kita dapat langsung mengetahui apakah perubahan yang dilakukan memengaruhi fungsionalitas yang sudah ada.

Jumlah unit test yang diperlukan bergantung pada kompleksitas kelas tersebut. Jika kelas memiliki banyak metode dengan berbagai skenario eksekusi, maka jumlah unit test yang dibutuhkan akan lebih banyak. Secara umum, kita harus membuat unit test untuk setiap metode yang memiliki logika bisnis penting dan kemungkinan jalur eksekusi yang berbeda. Unit test juga harus mencakup skenario positif (happy path) dan skenario negatif, seperti input yang tidak valid atau kondisi yang tidak terduga. Selain itu, unit test harus mencakup edge cases, yaitu kondisi ekstrem yang mungkin jarang terjadi tetapi bisa menyebabkan kesalahan jika tidak ditangani dengan baik.

Untuk mengetahui apakah unit test yang dibuat sudah cukup, salah satu cara yang dapat digunakan adalah dengan mengukur code coverage. Code coverage menunjukkan sejauh mana kode sumber telah diuji oleh unit test, biasanya dalam bentuk persentase. Namun, meskipun memiliki 100% code coverage, itu tidak berarti bahwa kode kita bebas dari bug atau kesalahan. Code coverage hanya menunjukkan bahwa kode telah dieksekusi, tetapi tidak menjamin bahwa logika di dalamnya sudah benar. Misalnya, sebuah tes mungkin hanya menjalankan kode tanpa benar-benar memverifikasi apakah hasilnya sesuai dengan yang diharapkan. Oleh karena itu, selain mengejar code coverage yang tinggi, kita juga harus memastikan bahwa unit test memiliki asersi yang kuat untuk memvalidasi output.

> Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!

Pembuatan kelas pengujian fungsional baru yang memiliki prosedur setup dan variabel instance yang sama dengan *CreateProductFunctionalTest.java* berpotensi mengurangi kualitas kode karena adanya duplikasi kode. Duplikasi ini dapat menyebabkan kode menjadi lebih sulit dipelihara dan rentan terhadap inkonsistensi jika terjadi perubahan pada prosedur setup di salah satu kelas, tetapi tidak diperbarui di kelas lainnya. Selain itu, kode yang berulang juga bertentangan dengan prinsip *DRY* (Don't Repeat Yourself), yang merupakan salah satu prinsip utama dalam clean code. Untuk meningkatkan kebersihan kode, sebaiknya prosedur setup yang digunakan oleh beberapa kelas pengujian dipindahkan ke dalam kelas induk (*superclass*) atau dibuat sebagai utilitas yang dapat digunakan kembali. Dengan cara ini, setiap kelas pengujian hanya perlu mewarisi atau memanggil metode setup tersebut tanpa perlu menuliskannya ulang. Selain itu, jika terdapat kesamaan dalam pola pengujian, pendekatan berbasis parameterisasi menggunakan *JUnit Parameterized Tests* atau *Test Factory* juga dapat diterapkan untuk mengurangi duplikasi dan meningkatkan modularitas kode. Hal ini tidak hanya meningkatkan keterbacaan dan efisiensi kode, tetapi juga mempermudah pemeliharaan serta memastikan bahwa setiap perubahan pada prosedur setup atau mekanisme pengujian dapat diterapkan secara konsisten di seluruh suite pengujian.

</details>