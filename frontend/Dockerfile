FROM node:20-alpine AS builder

ARG VITE_API_URL
ARG VITE_KEYCLOAK_URL
ENV VITE_API_URL=$VITE_API_URL
ENV VITE_KEYCLOAK_URL=$VITE_KEYCLOAK_URL

WORKDIR /app

COPY package.json yarn.lock ./
RUN yarn install

COPY . .
RUN yarn build

FROM node:20-alpine

WORKDIR /app

RUN yarn global add serve

COPY --from=builder /app/dist ./dist

EXPOSE 3000

CMD ["serve", "-s", "dist", "-l", "3000"]
