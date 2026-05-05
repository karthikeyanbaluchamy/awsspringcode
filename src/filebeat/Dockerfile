FROM docker.elastic.co/beats/filebeat:8.12.0

USER root

COPY filebeat.yml /usr/share/filebeat/filebeat.yml

RUN chown root:filebeat /usr/share/filebeat/filebeat.yml \
    && chmod 640 /usr/share/filebeat/filebeat.yml

USER filebeat
