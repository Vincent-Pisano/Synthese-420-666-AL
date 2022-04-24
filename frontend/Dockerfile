FROM node:latest AS builder
WORKDIR .

COPY package.json .
RUN npm install --silent

COPY . ./
RUN npm run build

CMD ["npm", "start"]