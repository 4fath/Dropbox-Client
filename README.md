# Dropbox Command-Line Tool

This is a command-line tool to help basic operations on Dropbox API v2.

Supported commands and usages here: 
<pre>
    <code>> auth {appKey} {appSecret} </code>
    <code>> info {accessToken} {locale} </code>
    <code>> list {accessToken} {path} {locale} </code>
</pre>

User should authenticate with <code>auth</code> that returns <code>accessToken</code> to use <code>info</code> and <code>list</code> command.
 
