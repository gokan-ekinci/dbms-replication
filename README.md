# dbms-replication
An API for replicating databases and manipulating Java beans with SQL queries


## InMemorySQL: How to use it

This is how you use this API, first let's create some dumb classes:

```
import fr.ekinci.dbmsreplication.inmemorysql.InMemorySQL;
//...

String sqlRequest = "SELECT t1.id_d1 AS id_d1, t1.attr2 AS attr2, t1.attr3 AS attr3, t2.id_d2 AS id_d2, t2.attr4 AS attr4, t3.id_d3 AS id_d3, t3.attr5 AS attr5 " +
    " FROM t1" +
    " LEFT JOIN t2" +
    " ON t1.id_d1 = t2.id_d1" +
    " LEFT JOIN t3" +
    " ON t2.id_d2 = t3.id_d3" +
    " ORDER BY t1.id_d1, t2.id_d2, t3.id_d3";

List<Dumb1> ldb1 = new ArrayList<>();       // fill it
Set<Dumb2> ldb2 = new HashSet<>();          // fill it
Collection<Dumb3> ldb3 = new ArrayList<>(); // fill it

List<ReturnDumb> result = new InMemorySQL()
    .add(Dumb1.class, ldb1) // ldb1 alias will be t1
    .add(Dumb2.class, ldb2) // ldb2 alias will be t2
    .add(Dumb3.class, ldb3) // ldb3 alias will be t3
    .executeQuery(ReturnDumb.class, sqlRequest);
```

See the complete test [here](https://github.com/eau-de-la-seine/dbms-replication/blob/master/src/test/java/fr/ekinci/dbmsreplication/test/InMemoryTest.java).

Pretty cool thing about this API:

* Never seen an other API which do that
* You're free to put the SQL query you want (it uses HSQLDB/HyperSQL implementation, you can use INNER JOIN, LEFT/RIGHT JOIN etc)
* You can use placeholders (`executeQuery()` has this signature : `executeQuery(yourReturnType : Class<T>, sqlQuery : String, parameters : Object...)`
* Your classes do not require to be related (no inheritance), you're free to inherit from the class you want.
* Pretty fast, bench it ;)


Things you have to know:

* This API use your fields (not getters or annotations) + the API will get fields from super classes.
* Your collections/lists have `t1`, `t2`, `tn`, `tn+1` aliases (in adding order).
* Your `DumbN` has some constraints :
    * Use only primitive (or wrappers) and String for attribute types.
* Your `ReturnClass` (`ReturnDumb` in the example above) has some constraints :
    * Use Java Wrappers for `ReturnClass` because primitive types do not handle `null` in Java
    * `INT` field in SQL means `Integer` in Java, don't use Java's `Long` or `BigInteger`
    * `BIGINTEGER` field in SQL means `Long` in Java, don't use Java's `Integer` or `BigInteger`
    * `DECIMAL` field in SQL means `BigDecimal` in Java, don't use `Double`
* Your SQL query has some constraints :
    * Only aliases will be retrieved (ex: `SELECT t1.foo AS myReturnClassAttributeName, t1.wontBeRetrievedBecauseNoAlias FROM t1`).
    * Don't use subquery in the first `SELECT` statement (don't do this: `SELECT (SELECT t1.field FROM t1) AS field FROM t1`).
    * Do not use sql-reserved keywords for fields, or even try to escape it with \` or preceding keyword with `_` may not work (ex: `_Group` or \``Group`\`).
* This API may need more tests, it has only been tested with less than 10_000 tuples.
