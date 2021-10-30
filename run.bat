@echo off
start java -Xmx1024m -jar -Dspring.profiles.active=dev build\libs\ngx-blobs-uaa-1.0.0.jar &
