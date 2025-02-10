# TrendApp

## Proje Açıklaması

TrendApp, belirlenen platformlardan (Reddit, YouTube, Twitter, Instagram) en popüler içerikleri toplayarak tek bir sayfada listeleyen bir web uygulamasıdır. Backend tarafında Spring Boot kullanılarak API entegrasyonu sağlanmıştır, frontend kısmı ise React.js ile geliştirilmiştir.

---

## Kurulum

### **Gereksinimler:**

- **Node.js** (v16 veya üstü)
- **Java** (JDK 17 veya üstü)
- **Maven**
- **PostgreSQL** (opsiyonel, şu an için veritabanı kullanılmamaktadır)

### **Backend Kurulumu (Spring Boot - Java)**

1. **Proje dizinine git:**
   ```sh
   cd trendapp-backend
   ```
2. **Bağımlılıkları yükleyin ve çalıştırın:**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
3. **API'nin çalıştığını test edin:**
   ```sh
   curl http://localhost:8080/api/trending
   ```

### **Frontend Kurulumu (React.js)**

1. **Frontend dizinine gidin:**
   ```sh
   cd trendapp-ui
   ```
2. **Bağımlılıkları yükleyin:**
   ```sh
   npm install
   ```
3. **Uygulamayı çalıştırın:**
   ```sh
   npm start
   ```
4. **Tarayıcıda açın:**
   ```
   http://localhost:3000
   ```

---

## API Kullanımı

| Endpoint                  | Açıklama                             |
| ------------------------- | ------------------------------------ |
| `GET /api/reddit`         | Reddit trend gönderilerini döner     |
| `GET /api/youtube/trends` | YouTube popüler videolarını döner    |
| `GET /api/twitter`        | Twitter trend başlıklarını döner     |
| `GET /api/instagram`      | Instagram popüler içeriklerini döner |

---

## **Olası Hatalar ve Çözümler**

### **1. Port Çakışması (3000 veya 8080 kullanılıyor)**

- **CMD üzerinden çalışan süreci durdur:**
  ```sh
  netstat -ano | findstr :3000
  taskkill /PID <PID_NUMARASI> /F
  ```

### **2. CORS Hatası**

- Backend'de `CorsConfig.java` dosyasında şu ayarın tanımlandığından emin olun:
  ```java
  @CrossOrigin(origins = "http://localhost:3000")
  ```

### **3. JSON Parse Hatası**

- API'nin JSON yerine HTML veya hata mesajı döndürüp döndürmediğini kontrol edin.
- `console.log(response.text())` ekleyerek yanıtın JSON formatında olup olmadığını test edin.

---

## **Geliştiriciler İçin Notlar**

- **Backend için:** `trendapp-backend/src/main/java/com/trendapp/appv01/` içinde controller ve service katmanları bulunmaktadır.
- **Frontend için:** `trendapp-ui/src/components/` içinde her platforma özel bileşenler yer almaktadır.
- **Hata logları:** `logs/` klasöründe tutulmaktadır.

**Katkıda bulunmak için:**

1. Fork alın.
2. Yeni bir branch açın.
3. Değişiklikleri yapıp PR oluşturun.

---

🎯 **Geliştirme aşamaları ve güncellemeler için takipte kalın!** 🚀

