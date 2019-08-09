# $name$

## How to setup dev stack

FIXME: add relevant steps on how to get a working dev environment and how to use this project

### Pre-Commit Hook

#### Scalafmt

NOTE: make sure that you have `scalafmt@2` locally installed. For that, follow the instruction on [scalafmt installation page](https://scalameta.org/scalafmt/docs/installation.html).

The relevant section are for Linux Users using `coursier`:

- Linux: [Install scalafmt via CLI](https://scalameta.org/scalafmt/docs/installation.html#cli)
- MacOS: [Install scalafmt via homebrew](https://scalameta.org/scalafmt/docs/installation.html#homebrew)

You can also run `scalafmt` through `sbt scalafmt` yet we use it for the pre-commit hook.

You can check that you have the right version via:

    scalafmt -v
    scalafmt 2.0.0-RC6

Any version >=2 should suffice.

#### pre-commit

We use [`pre-commit`](https://github.com/pre-commit/pre-commit) to setup and maintain shared commit hooks. Its configured in `.pre-commit-config.yaml`.

Install `pre-commit` either via `pip` OS independently:

    pip install pre-commit

or via [homebrew](https://brew.sh/) if you are on Mac OS:

    brew install pre-commit

#### Register the hooks

    pre-commit install -f --install-hooks

After that `scalafmt` checks your changed files for codestyle.

Conflicts MUST BE resolved. The pre-commit will only show the error, not automatically fix them. To fix some issues automatically, run `scalafmt` yourself.

## Tech Stack

FIXME:

- list
- all
- used
- techs
