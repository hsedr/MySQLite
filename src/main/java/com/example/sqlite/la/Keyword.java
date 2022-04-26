package com.example.sqlite.la;

import java.util.Locale;

/**
 * Token Class - Keyword
 */
public enum Keyword implements Type{
    Add("add"),
    AlterColumn("alter column"),
    AlterTable("alter table"),
    All("all"),
    Any("any"),
    As("as"),
    Asc("asc"),
    Between("between"),
    Case("case"),
    Check("check"),
    Column("column"),
    Create("create"),
    CreateDatabase("create database"),
    CreateIndex("create index"),
    CreateTable("create table"),
    Database("database"),
    Default("default"),
    Delete("delete"),
    Desc("desc"),
    Distinct("distinct"),
    DropColumn("drop column"),
    DropConstraint("drop constraint"),
    DropDatabase("drop database"),
    DropIndex("drop index"),
    DropTable("drop table"),
    DropView("drop view"),
    Exec("exec"),
    Exists("exists"),
    ForeignKey("foreign key"),
    From("from"),
    GroupBy("group by"),
    Having("having"),
    In("in"),
    Index("index"),
    InnerJoin(""),
    InsertInto("insert into"),
    IsNull("is null"),
    IsNotNull("is not null"),
    LeftJoin(""),
    Like("like"),
    Limit("limit"),
    NotNull("not null"),
    On("on"),
    OrderBy("order by"),
    Percent("percent"),
    PrimaryKey("primary key"),
    Procedure("procedure"),
    RightJoin(""),
    RowNum("row num"),
    Select("select"),
    Set("set"),
    Table("table"),
    Top("top"),
    Union("union"),
    Unique("unique"),
    Update("update"),
    Values("values"),
    View("view"),
    Where("where");

    String regEx;

    Keyword(String regEx){
        this.regEx = regEx;
    }

    @Override
    public boolean matches(String character){
        return character.toLowerCase(Locale.GERMANY).equals(this.regEx);
    }

    @Override
    public String getTag(){
        return regEx;
    }
}
