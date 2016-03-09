# dbms-replication
An API for replicating databases and manipulating Java beans with SQL queries


## InMemorySQL: How to use it

This is how you use this API, first let's create some dumb classes:

```
import fr.ekinci.dbmsreplication.inmemorysql.InMemorySQL;
//...

String sqlRequest = "SELECT t1.id_d1, t1.attr2, t1.attr3, t2.id_d2, t2.attr4, t3.id_d3, t3.attr5 " +
    " FROM t1" +
    " LEFT JOIN t2" +
    " ON t1.id_d1 = t2.id_d1" +
    " LEFT JOIN t3" +
    " ON t2.id_d2 = t3.id_d3" +
    " ORDER BY t1.id_d1, t2.id_d2, t3.id_d3";

List<Dumb1> ldb1 = new ArrayList<>(); // fill it
List<Dumb2> ldb2 = new ArrayList<>(); // fill it
List<Dumb3> ldb3 = new ArrayList<>(); // fill it

Collection<ReturnDumb> result = new InMemorySQL()
    .add(Dumb1.class, ldb1) // ldb1 alias will be t1
    .add(Dumb2.class, ldb2) // ldb2 alias will be t2
    .add(Dumb3.class, ldb3) // ldb3 alias will be t3
    .executeQuery(ReturnDumb.class, sqlRequest);
```

See the complete test [here](https://github.com/eau-de-la-seine/dbms-replication/blob/master/src/test/java/fr/ekinci/inmemory/test/MainTest.java).

Pretty cool thing about this API:

* Never seen an other API which do that
* You're free to put the SQL query you want (it uses HSQLDB/HyperSQL implementation, you can use INNER JOIN, LEFT/RIGHT JOIN etc)
* You can use placeholders (`executeQuery()` has this signature : `executeQuery(yourReturnType : Class<T>, sqlQuery : String, parameters : Object...)`
* Your classes do not require to be related (no inheritance)
* Pretty fast, bench it ;)


Things you have to know:

* This API use your fields (not getters or annotations), you're free to inherit from the class you want (the API will get fields from super classes)
* Your collections/lists have `t1`, `t2`, `tn`, `tn+1` aliases (in adding order).
* You `ReturnClass` (`ReturnDumb` in the example above) has some constraints :
    * Use Java Wrappers for `ReturnClass` because primitive types do not handle `null` in Java
    * `DECIMAL` field in SQL means `BigDecimal` in Java, don't use `double`/`Double`
    * `INT` field in SQL means `Integer` in Java, don't use Java's `Long`
    * Do not use sql-reserved keywords for fields, or even try to escape it with \` or preceding keyword with `_` may not work (ex: `_Group` or \``Group`\`).
* This API may need more tests, it has only been tested with less than 10_000 tuples.

