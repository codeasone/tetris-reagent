FROM theasp/clojurescript-nodejs:shadow-cljs-alpine as build

WORKDIR /app
COPY package.json shadow-cljs.edn deps.edn /app/
RUN shadow-cljs npm-deps && npm install --save-dev shadow-cljs
COPY ./ /app/
RUN shadow-cljs release :app
RUN npm run styles:release

FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx
COPY resources/public /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]