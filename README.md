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