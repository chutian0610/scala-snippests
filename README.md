# Scala Snippests

## Requirements

- jdk 17+
- scala 3.x 
- sbt 1.9.x

## `?` operator

use rust like `?` operator to make complex logical more readable 

for example 

```scala 3
parseColumnNames(tokenStream).flatMap(columnNameList => {
  expectKeyWord(tokenStream, KEYWORDS.AS).flatMap(_ =>{
    expectToken(tokenStream, Tokens.leftParen).flatMap(_ => {
      parseQuery(tokenStream).flatMap(subQuery => {
        expectToken(tokenStream, Tokens.rightParen).flatMap(_ => {
          Right(WithQuery(
            position = name.position,
            name = name,
            columnNames = Some(columnNameList),
            query = subQuery))
        })
      })
    })
  })
})
```

```scala 3
import  Q._
Q{
  val columnNameList= parseColumnNames(tokenStream).?
  expectKeyWord(tokenStream, KEYWORDS.AS).?
  expectToken(tokenStream, Tokens.leftParen).?
  val subQuery= parseQuery(tokenStream).?
  expectToken(tokenStream, Tokens.rightParen).?
  Right(WithQuery(
        position = name.position, name = name,
        columnNames = Some(columnNameList),
        query = subQuery))
}
```
