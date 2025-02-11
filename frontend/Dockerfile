# Node image ile frontend build container'ını oluşturuyoruz
FROM node:18-alpine AS build

WORKDIR /app

# Package.json ve package-lock.json dosyalarını kopyalıyoruz
COPY package.json package-lock.json ./

ENV NODE_ENV=development

# Gerekli bağımlılıkları kuruyoruz
RUN npm install

ARG REACT_APP_API_URL
ENV REACT_APP_API_URL=${REACT_APP_API_URL}
# Tüm dosyaları kopyalıyoruz
COPY . .

# Build işlemini çalıştırıyoruz
RUN npm run build --verbose

# Nginx ile frontend sunuyoruz
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
