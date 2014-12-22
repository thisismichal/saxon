saxon
=====

Czech Numberer for Saxon-HE

Instructions:

Requires "-m" / "-s" / "-ž" ordinal="-x" for formatting ordinal numbers with the right ordinal grammatical cases in Czech.
Default behavior without supplying any ordinal parameters is to return ordinals suitable for date formatting ("druhého") up until number 31.

Wrapper:
The main class and Transform_cs perform as wrapper for Saxon-HE. Building it with Saxon9he.jar as dependency will register the Numberer_cs


