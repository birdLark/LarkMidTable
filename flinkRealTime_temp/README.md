# Flink SQL Submit

Currently, Flink 1.9 doesn't support to submit a DDL statement in SQL CLI, and also doesn't support to submit a SQL script.
This is not convenient for walkthrough and demo. That's why I created a such a project.

## How to use

1. Set your Flink and Kafka install path in `env.sh`
2. Add your SQL scripts under `src/main/resources/` with `.sql` suffix, e.g, `q1.sql`
3. Start all the service your job needed, including Flink, Kafka, and DataBases.
3. Run your SQL via `./run.sh <sql-file-name>`, e.g. `./run.sh q1`
4. If the terminal returns the following output, it means the job is submitted successfully.

```
Starting execution of program
Job has been submitted with JobID d01b04d7c8f8a90798d6400462718743
```

## Others

You can follow my WeChat Subscription Account for more information about this project.

<img src="https://img.alicdn.com/tfs/TB1.ajIlIbpK1RjSZFyXXX_qFXa-1004-541.png" width="500px" />

