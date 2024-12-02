{ pkgs ? import
    # (fetchTarball {
    #   url = "https://github.com/NixOS/nixpkgs/archive/nixos-unstable.tar.gz";
    # })
    <nixpkgs>
    { }
}:

pkgs.mkShell {
  nativeBuildInputs = with pkgs; [
    scala_2_13
    scalafmt
    sbt
    metals
    coursier
    openjdk
  ];

  # Set environment variables for Java and Scala
  JAVA_HOME = "${pkgs.openjdk}/lib/openjdk";
  SCALA_HOME = "${pkgs.scala_2_13}/lib/scala";

  # Cleaned-up shellHook for version information
  shellHook = ''
    echo "=============================="
    echo " Scala 2.13 Development Shell "
    echo "=============================="

    echo "Java version: $(${pkgs.openjdk}/bin/java -version 2>&1 | grep 'openjdk' | sed -n 's/.*"\([0-9.]*\)".*/\1/p')"
    echo "Scala version: $(${pkgs.scala_2_13}/bin/scala --version 2>&1 | sed -n 's/.*version \([0-9.]*\).*/\1/p')"
    # echo "SBT version: $(${pkgs.sbt}/bin/sbt --version | grep 'sbt script version:' | head -1 | awk -F': ' '{print $2}')"
    # echo "Metals version: $(${pkgs.metals}/bin/metals --version | grep 'metals' | head -1 | awk '{print $2}')"
    # echo "Coursier version: $(${pkgs.coursier}/bin/cs version 2>/dev/null || echo 'Coursier not installed')"

    echo ""
    echo "Shell environment set up successfully!"
  '';
}
