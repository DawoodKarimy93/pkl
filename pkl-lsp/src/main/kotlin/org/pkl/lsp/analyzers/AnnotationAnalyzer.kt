/**
 * Copyright © 2024 Apple Inc. and the Pkl project authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pkl.lsp.analyzers

import org.eclipse.lsp4j.DiagnosticSeverity
import org.pkl.lsp.ErrorMessages
import org.pkl.lsp.PklLSPServer
import org.pkl.lsp.ast.*

class AnnotationAnalyzer(private val server: PklLSPServer) : Analyzer() {
  override fun doAnalyze(node: Node, diagnosticsHolder: MutableList<PklDiagnostic>): Boolean {
    if (node is PklModule || node is PklClass || node is PklModuleDeclaration) return true
    if (node !is PklAnnotation) return false

    val type = node.type
    if (type !is PklDeclaredType) {
      diagnosticsHolder.add(
        PklDiagnostic(
          type ?: node,
          ErrorMessages.create("annotationHasNoName"),
          DiagnosticSeverity.Error
        )
      )
    }
    return false
  }
}