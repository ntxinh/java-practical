# Deploy Spring boot & ReactJS with Datastore on GAE
- GCP (Google Cloud Platform)
- GAE (Google App Engine)

# DEMO
====================================================
- Server: https://springboot-datastore.appspot.com/
- Client: http://reactjs-host.appspot.com/

# SERVER
====================================================

## FOLDER

```cd server```

## REQUIREMENT
- Java 8 (or 7)
- maven
- [gcloud tool](https://cloud.google.com/sdk/downloads)
- An account Google

## RUN LOCALLY

- Run this command in terminal:

```mvn appengine:devserver```

- View result at http://localhost:8080

## DEPLOY ON GAE

### 1. Create project on GCP

- Đăng nhập tài khoản Google
`gcloud auth login`

- Xem danh sách user đã đăng nhập
`gcloud auth list`

- Set active cho một tài khoản
`gcloud config set account YOUR-ACCOUNT`

- Tạo project
`gcloud projects create YOUR-PROJECT-ID`

- Xem danh sách các project
`gcloud projects list`

- Set active cho dự án
`gcloud config set project YOUR-PROJECT-ID`

- Tạo App Engine
`gcloud app create --region asia-northeast1`

- Xem danh sách đã cấu hình
`gcloud config list`

### 2. Deploy project

- Update src/main/webapp/WEB-INF/appengine-web.xml
```
<application>YOUR-PROJECT-ID</application>
<version>1</version>
```
- Then, run this command in terminal:

```mvn appengine:update```

### 3. Result

``` gcloud app browse ```

- Homepage:
https://YOUR-PROJECT-ID.appspot.com/

- API Customer:
https://YOUR-PROJECT-ID.appspot.com/api/customers

# CLIENT
====================================================

## FOLDER

```cd client```

## REQUIREMENT
- nodejs
- npm

## RUN LOCALLY

```npm start```

- View result at http://localhost:3000/
(Homepage with Create & Delete Customer)

## DEPLOY ON GAE

### 1. Create project on GCP

- Repeat step `Create project on GCP` with another YOUR-PROJECT-ID

### 2. Deploy project

- Change url in App.js

``` npm run build ```

- Copy `build` forder to `deploy` folder

``` gcloud app deploy ```

### 3. Result

``` gcloud app browse ```

https://YOUR-PROJECT-ID.appspot.com/