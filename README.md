
Shell Pages Demo
----

A demo rendering App shell pages during building.

http://shell-pages.respo.site/

### Develop

Workflow https://github.com/mvc-works/stack-workflow

To compile project:

```bash
boot build-advanced
export boot_deps=`boot show -c`
planck -c $boot_deps:src/ -i render.cljs
```

find compiled site in `target/`.

### License

MIT
