#!/bin/bash
# Update sources
cd /home/dev/trialmaple
git pl

#Build backend
cd /home/dev/trialmaple/backend
mvn clean package
cp target/trialmaple.jar /home/dev/app/trialmaple.jar
sudo systemctl restart trialmaple

# Build frontend
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"

cd /home/dev/trialmaple/frontend
nvm i
nvm use
npm i
npm run build
sudo rm -rf /var/www/trialmaple/*
sudo cp -r dist/* /var/www/trialmaple

# Build backoffice
cd /home/dev/trialmaple/backoffice
nvm i
nvm use
npm i
npm run build
sudo rm -rf /var/www/admin_tmmaple/*
sudo cp -r dist/* /var/www/admin_tmmaple
