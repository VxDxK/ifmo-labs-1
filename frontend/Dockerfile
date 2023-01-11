FROM node:16-alpine
WORKDIR /src
COPY package.json .
RUN npm install
COPY . .
EXPOSE 3001
CMD ["node", "app.js"]
