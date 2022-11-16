package org.apache.causeway.lab.experiments.wktpdfjs.home.res;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
public enum PdfJsVersion {
    CURRENT("2.16.105");

    @Getter @Accessors(fluent=true)
    final String versionLiteral;

}
