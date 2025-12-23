#!/bin/bash
DB_NAME="trialmaple_db"
BACKUP_DIR="/var/backups/trialmaple-db"
DATE=$(date +"%Y-%m-%d_%H-%M")
RETENTION_DAYS=7

# Backup
mysqldump --single-transaction --routines --triggers \
  $DB_NAME | gzip > "$BACKUP_DIR/${DB_NAME}_${DATE}.sql.gz"

# Clean old backups
find "$BACKUP_DIR" -type f -name "*.sql.gz" -mtime +$RETENTION_DAYS -delete

