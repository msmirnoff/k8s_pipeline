FROM nginx:stable

# The deployment pipeline is the main focus so a static HTML site is chosen
COPY html /usr/share/nginx/html