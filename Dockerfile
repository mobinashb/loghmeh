FROM node:13.12.0-alpine AS build
WORKDIR /loghmeh
ENV PATH /loghmeh/node_modules/.bin:$PATH
COPY package.json package-lock.json ./
RUN npm install --silent && npm install react-scripts@3.4.1 -g --silent
COPY public ./public
COPY src ./src
RUN npm run build
FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /loghmeh/build /usr/share/nginx/html
EXPOSE 80
ENTRYPOINT ["nginx", "-g", "daemon off;"]