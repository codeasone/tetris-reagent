# tetris-reagent

Tetris implemented using [reagent](https://github.com/reagent-project/reagent) with game-logic from [tetris-clj](https://github.com/codeasone/tetris-clj).

![Screenshot](./images/screenshot.png)

## Play
To play the `release` build:

```
yarn install
yarn run play
```

Then visit http://localhost:3000/

Controls:

- `ENTER` to start/resume play
- ⬅️ ⬇️ ➡️ to move current tetrimino
- ⬆️ to rotate current tetrimino
- `SPACE` to drop
- `ESC` to pause

Once you hit `Game Over` you can start again by reloading.

## Development

If you're using [asdf](https://github.com/asdf-vm/asdf) then you'll want to `asdf install`.

If not just make sure you have compatible `java`, `clojure`, and `nodejs` versions installed on your system.

Next, install the project dependencies with `yarn install`.

This project uses `shadow-cljs` and the build to bring up is `:app`.

If you usually start `shadow-cljs` in a separate terminal from your IDE then running `yarn dev` will watch for code changes and recompile/hot-reload as required.

For Emacs users `.dir-locals.el` is included with settings that will help ensure a `shadow-cljs` nREPL is started automatically via `shadow.cljs.devtools.server.nrepl` when invoking `cider-jack-in-cljs`.

Once up you can interact with the game at http://localhost:3000/ and the `tap>` inspector is available as usual at http://localhost:9630/inspect.
